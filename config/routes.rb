# frozen_string_literal: true

Rails.application.routes.draw do
  get '/403', to: 'web/errors#forbidden', as: :not_forbidden_errors
  get '/404', to: 'web/errors#not_found', as: :not_found_errors
  match '/500', to: 'web/errors#server_error', via: :all

  devise_for :users, controllers: { omniauth_callbacks: 'web/omniauth_callbacks',
                                    sessions: 'web/devise/sessions',
                                    registrations: 'web/devise/registrations' }

  scope module: :web do
    root 'home#index'
    resources :vacancies, only: %i[index show]
    # FIXME: фикс дирекшенов с точками типа node.js, убрать, когда определимся, нужно ли будем валидировать направления и запретим указывать точки
    resources :vacancy_filters, only: %i[show], constraints: { id: %r{[^/]+} }, format: false, defaults: { format: 'html' } do
      collection do
        get :search, format: true
      end
    end
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
      resources :vacancies
      resources :notifications, only: %i[index update]
      resource :newsletters, only: %i[edit update]
      resource :profile, only: %i[edit update show] do
        patch '/check_box', to: 'profiles#check_box'
      end
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
      resources :vacancies, only: %i[index edit update]
    end
  end
end
