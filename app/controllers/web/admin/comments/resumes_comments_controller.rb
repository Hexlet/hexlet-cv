# frozen_string_literal: true

class Web::Admin::Comments::ResumesCommentsController < Web::Admin::Comments::ApplicationController
  def index
    @resume_comments = Resume::Comment.includes(:user, :resume)
    @resume_answers = Resume::Answer.includes(:user, :resume, comments: :user).ransack(params[:q])

    @search = Resume::Comment.ransack(params[:q])
    @resumes_comments = @search.result(sort: 'created_at desc').includes(:resume).page(params[:page])
  end
end
