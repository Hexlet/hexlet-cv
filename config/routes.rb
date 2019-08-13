Rails.application.routes.draw do
  namespace :web do
    namespace :account do
      get 'resumes/index'
      get 'resumes/new'
      get 'resumes/create'
      get 'resumes/destroy'
    end
  end
  namespace :web do
    get 'sessions/new'
  end
  scope module: :web do
    root 'home#index'
    resource :session
    resources :resumes do
      scope module: :resumes do
        resources :answers
      end
    end
    resources :answers, only: [] do
      scope module: :answers do
        resources :comments
      end
    end

    namespace :account do
      resources :resumes
    end
  end
end
