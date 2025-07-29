import { useState } from 'react';
import { Modal, Title, Button, Group, TextInput, PasswordInput, Alert } from '@mantine/core';
import { Inertia } from '@inertiajs/inertia';
import { useForm } from '@mantine/form';

interface LoginResponseProps {
  props: {
    token?: string;
  };
}

function Login() {
  const [opened, setOpened] = useState(true);
  const [authError, setAuthError] = useState<string | null>(null); 
    
  const form = useForm({
    mode: 'uncontrolled', 
    initialValues: {
      email: '',
      password: '',
    },

    validate: {
      email: (value) =>
        /^\S+@\S+$/.test(value) ? null : 'Неверный E-mail',
      password: (value) =>
        value.length > 0 ? null : 'Пароль не должен быть пустым',
    },
  });

  const handleSubmit = (values: typeof form.values) => {
    setAuthError(null);

    Inertia.post(
      '/api/login',
      values,
      {
        onError: (errors: Record<string, string>) => {
          if (errors.email || errors.password) {
            form.setErrors(errors);
          } else {
            setAuthError('Пользователь не найден или пароль неверен');
          }
        },

        onSuccess: (page) => {
          const token = (page as LoginResponseProps).props.token;

          if (token) {
            localStorage.setItem('jwt', token);
            setOpened(false);
            Inertia.visit('/');
          } else {
            setAuthError('Не удалось получить токен');
          }
        },
      }
    );
  };


  return (
    <Modal opened={opened} onClose={() => setOpened(false)} centered withCloseButton={false} closeOnEscape={false}
      closeOnClickOutside={false}>
        <Title order={1} ta="center">Войти в систему</Title>
        <form onSubmit={form.onSubmit(handleSubmit)}>
            <TextInput 
                withAsterisk
                placeholder="E-mail"
                mt="md"
                key={form.key('email')}
                {...form.getInputProps('email')}
            />
            <PasswordInput
                withAsterisk
                placeholder="Пароль"
                mt="md"
                key={form.key('password')}
                {...form.getInputProps('password')}
            />

            {authError && (
              <Alert color="red" mt="sm">
                {authError}
              </Alert>
            )}

            <Group justify="center" mt="md">
                <Button type="submit" fullWidth>Войти</Button>
            </Group>
        </form>
    </Modal>
  )
}

export default Login;