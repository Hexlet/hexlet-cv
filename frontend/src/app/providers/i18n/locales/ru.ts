export default {
  translation: {
    homePage: {
      greetings: 'Добро пожаловать!',
      aboutUs: {
        title: 'Найти работу в IT легче, чем ты думаешь',
        description: 'С Hexlet Карьерой ты получаешь офферы быстрее - за недели, а не месяцы.',
        buttons: {
          tryFree: 'Попробовать бесплатно',
          startWithProjects: 'Начать с проектов →',
        },
      },
      whoWeAre: {
        title: 'Привет, мы Хекслет',
        subtitle: 'экосистема для старта и развития в IT:',
        features: {
          resume: 'Составлять резюме',
          apply: 'Откликаться',
          searchJobs: 'Искать вакансии и стажировки',
          chatRecruiters: 'Переписываться с рекрутерами',
          coverLetters: 'Писать сопроводительные',
          prepareInterviews: 'Готовить интервью',
          getExperience: 'Получать коммерческий опыт',
        },
      },
    },
    // ДОБАВЛЯЕМ ЭТО ↓
    auth: {
      email: 'Email',
      password: 'Пароль',
      confirmPassword: 'Подтвердите пароль',
      enterNewPassword: 'Введите новый пароль',
      confirmNewPassword: 'Подтвердите новый пароль',
      
      forgotPassword: {
        title: 'Восстановление пароля',
        description: 'Введите ваш email',
        cancel: 'Отмена',
        sendLink: 'Отправить ссылку',
        successMessage: 'Ссылка для сброса пароля отправлена на вашу почту',
        forgotPassword: 'Забыли пароль?',
      },
      
      resetPassword: {
        title: 'Смена пароля',
        changePassword: 'Сменить пароль',
        successMessage: 'Пароль успешно изменен!',
        goToLogin: 'Войти',
        passwordsNotMatch: 'Пароли не совпадают',
        passwordMinLength: 'Пароль должен быть не менее 6 символов',
        invalidToken: 'Неверная или просроченная ссылка',
        expiredToken: 'Ссылка для сброса пароля устарела',
        enterNewPasswordDescription: 'Введите новый пароль для вашей учетной записи',
        invalidTokenDescription: 'Ссылка для сброса пароля недействительна',
      },
    },
  },
};
