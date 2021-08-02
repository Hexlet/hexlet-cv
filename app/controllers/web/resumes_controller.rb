# frozen_string_literal: true

class Web::ResumesController < Web::ApplicationController
  impressionist actions: [:show]

  def index
    @resumes = Resume.web

    respond_to do |format|
      format.rss
    end
  end

  def show
    @resume = Resume.web.find(params[:id])
    authorize @resume

    @resume_answers = @resume.answers
                             .web
                             .joins(:comments)
                             .merge(Resume::Answer::Comment.web)
                             .order(likes_count: :desc)
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
end
