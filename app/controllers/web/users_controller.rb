# frozen_string_literal: true

class Web::UsersController < Web::ApplicationController
  def index
    @users = User.where('resume_answer_likes_count > ?', 0).order(resume_answer_likes_count: :desc)

    set_meta_tags canonical: users_url
    set_meta_tags og: {
      title: t('.title'),
      type: 'website',
      url: users_url
    }
  end

  def show
    @user = User.permitted.find(params[:id])
    @user_resume_answers = @user.resume_answers.web
    @user_resume_answers_likes_count = @user.resume_answers.sum('likes_count')
    @user_resumes = @user.resumes.web
    @user_resume_comments = @user.resume_comments.web.joins(:resume).merge(Resume.web)
    @career_members = @user.career_members.includes(:career, :career_step_members).without_archive_members

    if @career_members.any?
      @career_progress = @career_members.each_with_object({}) do |member, acc|
        acc[member.id] = {}
        acc[member.id][:career_name] = member.career.name
        acc[member.id][:career_slug] = member.career.slug
        acc[member.id][:finished_steps_count] = member.finished_steps_count
        acc[member.id][:career_progress] = member.progress_by_finished_steps
        acc[member.id][:career_progress] = member.progress_by_finished_steps
        acc[member.id][:finished_steps] = member.career_step_members.finished.map { |step_member| step_member.career_step.name }
      end
    end

    set_meta_tags canonical: user_url(@user)
    set_meta_tags og: {
      title: @user,
      type: 'website',
      url: user_url(@user)
    }
  end
end
