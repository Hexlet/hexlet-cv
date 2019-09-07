# frozen_string_literal: true

class Web::ResumesController < ApplicationController
  def show
    # FIXME: check if user already rated answers
    @resume = Resume.find(params[:id])
    @answer = Resume::Answer.new resume: @resume
    @current_user_answer = @resume.answers.find_by(user: current_user)
    current_user_likes = @resume.answer_likes.where(user: current_user)
    @current_user_likes_by_answer_id = current_user_likes.collect { |item| [item.resume_answer_id, item] }.to_h
  end
end
