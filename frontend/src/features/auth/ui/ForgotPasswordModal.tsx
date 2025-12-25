import { Modal, TextInput, Button, Text, Stack, Group } from '@mantine/core';
import { useForm } from '@mantine/form';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';

interface ForgotPasswordModalProps {
  opened: boolean;
  onClose: () => void;
}

export const ForgotPasswordModal: React.FC<ForgotPasswordModalProps> = ({ opened, onClose }) => {
  const { t } = useTranslation();
  
  const form = useForm({
    initialValues: {
      email: '',
    },
    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : t('auth.forgotPassword.invalidEmail')),
    },
  });

  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (values: { email: string }) => {
    setLoading(true);
    setError(null);

    try {
      await new Promise((resolve, reject) => {
        setTimeout(() => {
          console.log('Отправка запроса на восстановление пароля для:', values.email);
          resolve({ success: true });
        }, 1000);
      });

      setSuccess(true);
      
      // Автоматическое закрытие через 3 секунды
      setTimeout(() => {
        onClose();
        form.reset();
        setSuccess(false);
      }, 3000);
    } catch (err: any) {
      setError(
        err?.errors?.email?.[0] || 
        err?.message || 
        t('auth.forgotPassword.genericError')
      );
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    onClose();
    form.reset();
    setError(null);
    setSuccess(false);
  };

  return (
    <Modal
      opened={opened}
      onClose={onClose}
      title={t('auth.forgotPassword.title')}
      centered
      size="sm"
      radius="lg"
    >
      <form onSubmit={form.onSubmit(handleSubmit)}>
        <Stack gap="md">
          {success ? (
            <Text c="green" size="sm">
              {t('auth.forgotPassword.successMessage')}
            </Text>
          ) : (
            <>
              <Text size="sm" c="dimmed">
                {t('auth.forgotPassword.description')}
              </Text>
              
              <TextInput
                placeholder="E-mail"
                radius="md"
                {...form.getInputProps('email')}
                required
                type="email"
                disabled={loading}
                error={form.errors.email}
              />
              
              {error && (
                <Text c="red" size="sm">
                  {error}
                </Text>
              )}

              <Group gap="sm" grow>
                <Button
                  radius="md"
                  type="submit"
                  loading={loading}
                  disabled={!form.values.email || loading}
                >
                  {t('auth.forgotPassword.sendLink')}
                </Button>
                <Button
                  variant="default"
                  radius="md"
                  onClick={handleCancel}
                  disabled={loading}
                >
                  {t('auth.forgotPassword.cancel')}
                </Button>
              </Group>
            </>
          )}
        </Stack>
      </form>
    </Modal>
  );
};