class FillResumeEducationsDescriptionForOldResumes < ActiveRecord::Migration[6.0]
  def change
    Resume::Education.find_each do |education|
      education.update(description: [education.faculty, education.institution].select(&:present?).join(', '))
    end
  end
end
