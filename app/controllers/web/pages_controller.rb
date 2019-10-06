# frozen_string_literal: true

class Web::PagesController < ApplicationController
  def show
    render params[:id]
  end
end
