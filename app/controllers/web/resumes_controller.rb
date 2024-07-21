# frozen_string_literal: true

class Web::ResumesController < Web::ApplicationController
  redirect_actions_when_not_founded_to -> { root_path }, only: :show, if: -> { browser.bot? }

  def index
    @resumes = Resume.web.with_locale.order(id: :desc)

    respond_to do |format|
      format.rss
    end
  end

  def show
    @resume = Resume.web.with_locale.find(params[:id])
    authorize @resume

    impressionist(@resume) if inc_view_counter?

    @resume_answers = @resume.answers
                             .web
                             # .includes(:comments)
                             # .merge(Resume::Answer::Comment.web)
                             .order(likes_count: :desc)
                             .uniq
    @answer = Resume::Answer.new resume: @resume
    @current_user_answer = @resume.answers.find_by(user: current_user)
    current_user_likes = @resume.answer_likes.where(user: current_user)
    @current_user_likes_by_answer_id = current_user_likes.index_by(&:answer_id)
    @resume_educations = @resume.educations.web
    @resume_works = @resume.works.web

    set_meta_tags canonical: resume_url(@resume)
    set_meta_tags og: {
      title: @resume,
      description: @resume.summary,
      type: 'article',
      url: resume_url(@resume)
    }
  end

  def inc_view_counter?
    return true unless current_user

    !@resume.author?(current_user) && !@resume.impressions.exists?(user_id: current_user.id)
  end
end
