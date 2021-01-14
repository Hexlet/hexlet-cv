# frozen_string_literal: true

Rails.application.routes.draw do
  devise_for :users, controllers: { omniauth_callbacks: 'web/omniauth_callbacks' }

  scope module: :web do
    root 'home#index'
    resources :resumes do
      scope module: :resumes do
        resources :answers do
          member do
            patch :change_applying_state
          end
        end
        resources :pdfs, only: :show
        resources :comments
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
      resources :notifications, only: %i[index update]
      resource :newsletters, only: %i[edit update]
      resource :profile, only: %i[edit update show]
    end

    resources :users
    resources :pages

    scope module: :hooks do
      resource :sparkpost
    end

    namespace :admin do
      root 'home#index'
      resources :users, only: %i[index edit update]
      resources :resumes, only: %i[index edit update]
    end
  end
end
