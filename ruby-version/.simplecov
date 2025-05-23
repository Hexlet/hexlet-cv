# frozen_string_literal: true

SimpleCov.profiles.define 'custom rails' do
  add_filter '/config/'
  add_filter 'test'
  add_filter 'vendor'

  add_group 'Models', 'app/models'
  add_group 'Mutators', 'app/mutators'
  add_group 'Policies', 'app/policies'
  add_group 'Repositories', 'app/repositories'
  add_group 'Libraries', 'lib'

  add_group 'Web/Controllers', 'app/controllers/web'
  add_group 'Web/Helpers', 'app/helpers/web'
end
