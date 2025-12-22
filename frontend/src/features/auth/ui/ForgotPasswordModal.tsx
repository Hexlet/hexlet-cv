import { Modal, TextInput, Button, Text, Stack, Group } from '@mantine/core';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';

interface ForgotPasswordModalProps {
  opened: boolean;
  onClose: () => void;
}

export const ForgotPasswordModal: React.FC<ForgotPasswordModalProps> = ({ opened, onClose }) => {
  const { t } = useTranslation();
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    
    // ЗАГЛУШКА: Имитация отправки запроса
    setTimeout(() => {
      console.log('Отправка ссылки на:', email);
      setLoading(false);
      setSuccess(true);
      
      // Закрываем через 3 секунды
      setTimeout(() => {
        onClose();
        setSuccess(false);
        setEmail('');
      }, 3000);
    }, 1000);
  };

  const handleCancel = () => {
    onClose();
    setEmail('');
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
      <form onSubmit={handleSubmit}>
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
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                type="email"
                disabled={loading}
              />
              
              <Group gap="sm" grow>
                  <Button
                  radius="md"
                  type="submit"
                  loading={loading}
                  disabled={!email || loading}
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