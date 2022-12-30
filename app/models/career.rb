# frozen_string_literal: true

module Career
  GOALS = {
    'work_course' => {
      links: [
        {
          caption: 'Курс "Трудоустройство"',
          link: 'https://ru.hexlet.io/courses/employment'
        }
      ]
    },
    'resume' => {},
    'letter' => {},
    'open_source' => {
      links: [
        {
          caption: 'Как выбрать опен-сорс проект',
          link: 'https://ru.hexlet.io/blog/posts/kak-vybrat-svoy-pervyy-open-sors-proekt-instruktsiya-ot-heksleta'
        }
      ]
    },
    'code_battle' => {
      links: [
        {
          caption: "Codebattle by Hexlet's community",
          link: 'https://codebattle.hexlet.io/'
        }
      ]
    },
    'hexlet_tests_repo' => {
      links: [
        {
          link: 'https://github.com/Hexlet/ru-test-assignments',
          caption: 'Список тестовых заданий для прокачки'
        }
      ]
    },
    'github_profile_review' => {},
    'interview' => {}
  }.freeze
end
