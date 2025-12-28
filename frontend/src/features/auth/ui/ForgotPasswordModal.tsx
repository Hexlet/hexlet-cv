import { Modal, TextInput, Button, Text, Stack, Group, Alert } from '@mantine/core';
import { useForm } from '@inertiajs/react';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';

interface ForgotPasswordModalProps {
  opened: boolean;
  onClose: () => void;
}

export const ForgotPasswordModal: React.FC<ForgotPasswordModalProps> = ({ opened, onClose }) => {
  const { t } = useTranslation();
  const [formError, setFormError] = useState<string | null>(null);

  const form = useForm({
    email: '',
  });

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setFormError(null);

    if (!/^\S+@\S+$/.test(form.data.email)) {
      setFormError(t('auth.forgotPassword.invalidEmail'));
      return;
    }

    form.post('/forgot-password', {
      onError: (errors) => {
        console.error('Ошибка восстановления пароля:', errors);
        
        if (errors?.email) {
          setFormError(errors.email);
        } else if (errors?.message) {
          setFormError(errors.message);
        } else {
          setFormError(t('auth.forgotPassword.genericError'));
        }
      },
      onSuccess: () => {
        console.log('Запрос на восстановление пароля отправлен');
        // Закрываем модальное окно сразу после успеха
        onClose();
        form.reset();
      },
    });
  };

  const handleCancel = () => {
    onClose();
    form.reset();
    setFormError(null);
  };

  return (
    <Modal
      opened={opened}
      title={t('auth.forgotPassword.title')}
      centered
      size="sm"
      radius="lg"
      onClose={handleCancel}
    >
      <form onSubmit={handleSubmit}>
        <Stack gap="md">
          <Text size="sm" c="dimmed">
            {t('auth.forgotPassword.description')}
          </Text>
          
          <TextInput
            placeholder={t('auth.email')}
            radius="md"
            value={form.data.email}
            onChange={(e) => form.setData('email', e.currentTarget.value)}
            required
            type="email"
            disabled={form.processing}
            error={form.errors.email}
          />

          {formError && (
             <Alert 
              variant="filled" 
              color="red" 
              radius="md"
              title={formError}
              withCloseButton
              onClose={() => setFormError(null)}
            />
          )}

          <Group gap="sm" grow>
            <Button
              radius="md"
              type="submit"
              loading={form.processing}
              disabled={form.processing}
            >
              {t('auth.forgotPassword.sendLink')}
            </Button>
            <Button
              variant="default"
              radius="md"
              onClick={handleCancel}
              disabled={form.processing}
            >
              {t('auth.forgotPassword.cancel')}
            </Button>
          </Group>
        </Stack>
      </form>
    </Modal>
  );
};