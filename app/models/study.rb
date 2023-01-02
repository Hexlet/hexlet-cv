# frozen_string_literal: true

module Study
  GOALS = {
    'work_course' => {
      articles: [
        {
          caption: 'Курс "Трудоустройство"',
          link: 'https://ru.hexlet.io/courses/employment'
        }
      ]
    },
    'resume' => {},
    'letter' => {},
    'open_source' => {
      articles: [
        {
          caption: 'Как выбрать опен-сорс проект',
          link: 'https://ru.hexlet.io/blog/posts/kak-vybrat-svoy-pervyy-open-sors-proekt-instruktsiya-ot-heksleta'
        },
        {
          caption: 'Как участвовать в open source проектах',
          linl: 'https://ru.hexlet.io/blog/posts/participate-in-open-source'
        },
        {
          caption: 'Как принять участие в Open Source. Краткое руководство',
          link: 'https://ru.hexlet.io/blog/posts/Open-Source-github'
        }
      ]
    },
    'code_battle' => {
      articles: [
        {
          caption: "Codebattle by Hexlet's community",
          link: 'https://codebattle.hexlet.io/'
        }
      ]
    },
    'hexlet_tests_repo' => {
      articles: [
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
