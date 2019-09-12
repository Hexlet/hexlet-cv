# frozen_string_literal: true

class Web::ResumesController < ApplicationController
  def show
    @resume = Resume.find(params[:id])
    authorize @resume

    @resume_answers = @resume.answers.includes(:comments).order(likes_count: :desc)
    @answer = Resume::Answer.new resume: @resume
    @current_user_answer = @resume.answers.find_by(user: current_user)
    current_user_likes = @resume.answer_likes.where(user: current_user)
    @current_user_likes_by_answer_id = current_user_likes.collect { |item| [item.answer_id, item] }.to_h
    @resume_educations = @resume.educations.order(begin_date: :desc)
    @resume_works = @resume.works.order(begin_date: :desc)

    title @resume

    set_meta_tags description: @resume.summary,
                  canonical: resume_url(@resume)
    set_meta_tags og: {
      title: meta_tag_title(@resume),
      description: @resume.summary,
      type: 'article',
      url: resume_url(@resume)
    }
  end
end
