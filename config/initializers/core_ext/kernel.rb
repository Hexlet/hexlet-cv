# frozen_string_literal: true

module Kernel
  def let(value)
    yield value
  end

  def let_if(value)
    yield value if value
  end
end
