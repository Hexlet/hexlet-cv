class RemoveUserVadimPhilimonov < ActiveRecord::Migration[7.0]
  def change
    user = User.find_by(email: 'philimonov.vadim@gmail.com')
  
    return unless user

    user.resumes.each do |resume|
      resume.answers.each(&:destroy!)
      resume.destroy!
    end

    user.notifications.each(&:destroy!)

    user.destroy!
  end
end
