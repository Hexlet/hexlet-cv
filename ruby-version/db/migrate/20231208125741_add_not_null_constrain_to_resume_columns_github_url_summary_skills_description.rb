class AddNotNullConstrainToResumeColumnsGithubUrlSummarySkillsDescription < ActiveRecord::Migration[7.0]
  def change
    change_column_null :resumes, :github_url, false
    change_column_null :resumes, :summary, false
    change_column_null :resumes, :skills_description, false
  end
end
