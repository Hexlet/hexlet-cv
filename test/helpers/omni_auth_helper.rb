module OmniAuthHelper
  OmniAuth.config.test_mode = true
  OmniAuth.config.add_mock(:github, {
    provider: 'github',
    uid: '12345',
    info: { name: 'Github User', email: 'github@github.com' }
  })

  def sign_with_github
    Rails.application.env_config["omniauth.auth"] = OmniAuth.config.mock_auth[:github]
    get user_github_omniauth_callback_path
  end
end