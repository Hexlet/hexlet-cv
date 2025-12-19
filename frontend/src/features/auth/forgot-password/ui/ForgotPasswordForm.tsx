import { useState } from 'react';
import { useForm } from '@mantine/form';
import { Button, TextInput, Notification, Paper, Title, Text } from '@mantine/core';
import { useTranslation } from 'react-i18next';
import { forgotPassword } from '../model/forgot-password';

export function ForgotPasswordForm() {
  const { t } = useTranslation();
  const [isLoading, setIsLoading] = useState(false);
  const [notification, setNotification] = useState<{type: 'success' | 'error', message: string} | null>(null);

  const form = useForm({
    initialValues: {
      email: '',
    },
    validate: {
      email: (value) => {
        if (!value) return t('auth.errors.emailRequired');
        if (!/^\S+@\S+$/.test(value)) return t('auth.errors.emailInvalid');
        return null;
      },
    },
  });

  const handleSubmit = async (values: { email: string }) => {
    setIsLoading(true);
    setNotification(null);

    try {
      await forgotPassword(values.email);
      setNotification({
        type: 'success',
        message: t('auth.forgotPassword.successMessage'),
      });
      form.reset();
    } catch (error) {
      setNotification({
        type: 'error',
        message: error instanceof Error 
          ? error.message 
          : t('auth.errors.somethingWentWrong'),
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Paper withBorder shadow="md" p="xl" radius="md" style={{ maxWidth: 400, margin: '0 auto' }}>
      <Title order={2} mb="md" ta="center">
        {t('auth.forgotPassword.title')}
      </Title>
      
      <Text size="sm" mb="xl" ta="center">
        {t('auth.forgotPassword.description')}
      </Text>

      {notification && (
        <Notification
          color={notification.type === 'success' ? 'teal' : 'red'}
          title={notification.type === 'success' 
            ? t('auth.forgotPassword.successTitle') 
            : t('auth.forgotPassword.errorTitle')}
          onClose={() => setNotification(null)}
          mb="md"
          withCloseButton
        >
          {notification.message}
        </Notification>
      )}

      <form onSubmit={form.onSubmit(handleSubmit)}>
        <TextInput
          label={t('auth.fields.email')}
          placeholder="user@example.com"
          required
          {...form.getInputProps('email')}
          mb="md"
        />

        <Button 
          type="submit" 
          fullWidth 
          loading={isLoading}
          disabled={isLoading}
        >
          {t('auth.forgotPassword.sendResetLink')}
        </Button>
      </form>
    </Paper>
  );
}
