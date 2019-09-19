# frozen_string_literal: true

Rails.application.routes.draw do
  devise_for :users

  scope module: :web do
    root 'home#index'
    resources :resumes do
      scope module: :resumes do
        resources :answers
        resources :comments, only: %i[create update destroy]
      end
    end
    resources :answers, only: [] do
      scope module: :answers do
        resources :comments
        resources :likes, only: %i[create destroy]
        resources :comments, only: %i[create update destroy]
      end
    end

    namespace :account do
      resources :resumes
      resource :profile, only: %i[edit update show]
    end

    resources :users
  end
end
