# frozen_string_literal: true

require 'test_helper'

class ResumeTest < ActiveSupport::TestCase
  test 'allows to hide public resume' do
    resume = resumes(:one)
    resume.hide

    assert resume.state == 'draft'
  end
  # test "the truth" do
  #   assert true
  # end
end
