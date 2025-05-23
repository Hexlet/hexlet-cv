# frozen_string_literal: true

class Slug
  def self.encode(value)
    Translit.convert(value)
  end

  def self.decode(value)
    Translit.convert(value)
  end
end
