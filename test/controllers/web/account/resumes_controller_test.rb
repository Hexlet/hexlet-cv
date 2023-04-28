# frozen_string_literal: true

require 'test_helper'

class Web::Account::ResumesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @user = users(:one)
    sign_in(@user)
  end

  test '#index' do
    get account_resumes_path
    assert_response :success
  end

  test '#new' do
    get new_account_resume_path
    assert_response :success
  end

  test '#create and published' do
    user_special = users(:special)
    attrs = FactoryBot.attributes_for :resume
    education_attrs = attrs[:educations_attributes].first
    works_attrs = attrs[:works_attributes].first
    params = {
      publish: true,
      resume: attrs
    }

    post(account_resumes_path, params:)
    assert_response :redirect

    resume = Resume.find_by(name: attrs[:name])

    last_answer = resume.answers.last
    assert { resume }
    assert { resume.educations.exists?(description: education_attrs[:description]) }
    assert { resume.works.exists?(company: works_attrs[:company]) }
    assert { resume.evaluated? }
    assert { last_answer.user_id == user_special.id }
  end

  test '#create and to draft' do
    attrs = FactoryBot.attributes_for :resume

    params = {
      hide: true,
      resume: attrs
    }

    post(account_resumes_path, params:)
    assert_response :redirect

    resume = Resume.find_by(name: attrs[:name])
    assert { resume.not_evaluated? }
  end

  test '#update evaluated_ai_state failed' do
    resume = resumes(:one_evaluated_failed)
    attrs = FactoryBot.attributes_for(:resume)

    patch account_resume_path(resume), params: { resume: attrs }

    assert_response :redirect
    resume.reload

    assert { resume.evaluated? }
  end

  test '#edit' do
    resume = resumes(:one)
    get edit_account_resume_path(resume)
    assert_response :success
  end

  test '#update' do
    user_special = users(:special)
    resume = resumes(:one)
    work = resume_works(:one)

    work_attrs = FactoryBot.attributes_for 'resume/work'
    attrs = FactoryBot.attributes_for(:resume,
                                      works_attributes: { work.id => work.attributes.merge(company: work_attrs[:company]) })
    patch account_resume_path(resume), params: { resume: attrs }
    assert_response :redirect

    resume.reload
    work.reload
    last_answer = resume.answers.last
    assert { resume.name == attrs[:name] }
    assert { work.company == work_attrs[:company] }
    assert { resume.evaluated? }
    assert { last_answer.user_id == user_special.id }
  end

  test 'should publish published resume' do
    resume = resumes(:one)
    attrs = FactoryBot.attributes_for :resume

    params = {
      publish: true,
      resume: { name: attrs[:name] }
    }

    patch(account_resume_path(resume), params:)
    assert_response :redirect
  end

  test 'should hide published resume' do
    resume = resumes(:one)

    params = {
      hide: true,
      resume: { name: resume.name }
    }
    patch(account_resume_path(resume), params:)
    assert_response :redirect
    resume.reload
    assert { resume.draft? }
  end

  test '#create whith valid length tags direction' do
    attrs = FactoryBot.attributes_for :resume
    attrs[:direction_list] = 'Ruby-dveloper'

    post account_resumes_path, params: { resume: attrs }
    assert_response :redirect

    resume = Resume.find_by(name: attrs[:name])
    assert { resume }
  end

  test 'do not create if tag length exceeds 40 characters' do
    attrs = FactoryBot.attributes_for :resume
    attrs[:direction_list] = 'Ruby' * 40

    post account_resumes_path, params: { resume: attrs }
    assert_response :unprocessable_entity

    resume = Resume.find_by(name: attrs[:name])
    assert { !resume }
  end
end
