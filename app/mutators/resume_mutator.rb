# frozen_string_literal: true

class ResumeMutator
  class << self
    def publish!(resume, params = {})
      resume.update(params)
      resume.publish!
    end

    def to_draft!(resume, params = {})
      resume.hide!
      resume.update(params)
    end
  end
end
