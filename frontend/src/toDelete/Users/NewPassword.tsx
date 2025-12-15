import React, { useState, useEffect } from 'react'
import { useForm, Link } from '@inertiajs/react'
import { 
  Container, 
  Title, 
  PasswordInput, 
  Button, 
  Alert, 
  Group, 
  Text,
  Card,
  LoadingOverlay
} from '@mantine/core'

export default function NewPassword() {
  const [passwordError, setPasswordError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)
  
  const urlParams = new URLSearchParams(window.location.search)
  const token = urlParams.get('token')
  
  const form = useForm({
    password: '',
    password_confirmation: '',
    token: token || '',
  })

  useEffect(() => {
    if (token) {
      form.setData('token', token)
    }
  }, [token])

  const validatePassword = () => {
    if (form.data.password.length < 8) return 'Пароль должен быть минимум 8 символов'
    if (form.data.password !== form.data.password_confirmation) return 'Пароли не совпадают'
    return null
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    setPasswordError(null)
    
    const err = validatePassword()
    if (err) {
      setPasswordError(err)
      return
    }

    form.put('/en/users/password', {
      onSuccess: () => {
        setSuccess(true)
        form.reset()
      },
      onError: (errs) => {
        console.error('Ошибка смены пароля', errs)
        
        if (errs.token) {
          if (errs.token.includes('expired')) {
            setPasswordError('Ссылка для сброса пароля устарела')
          } else if (errs.token.includes('invalid')) {
            setPasswordError('Недействительная ссылка для сброса пароля')
          } else {
            setPasswordError('Ошибка при смене пароля')
          }
        } else {
          setPasswordError('Произошла ошибка. Попробуйте позже.')
        }
      },
    })
  }

  if (!token) {
    return (
      <Container size="sm" mt="xl">
        <Card shadow="sm" padding="lg" radius="md" withBorder>
          <Alert color="red" title="Ошибка">
            <Text>Отсутствует токен для сброса пароля. Пожалуйста, запросите новую ссылку.</Text>
          </Alert>
          <Group justify="center" mt="md">
            <Button component={Link} href="/en/users/sign_in" variant="light">
              Вернуться к входу
            </Button>
          </Group>
        </Card>
      </Container>
    )
  }

  return (
    <Container size="sm" mt="xl">
      <Card shadow="sm" padding="lg" radius="md" withBorder>
        <LoadingOverlay visible={form.processing} />
        
        {success ? (
          <div>
            <Alert 
              color="green" 
              title="Пароль успешно изменен!"
              mb="md"
            >
              <Text>Теперь вы можете войти в систему с новым паролем.</Text>
            </Alert>
            <Group justify="center">
              <Button component={Link} href="/en/users/sign_in" size="md">
                Войти
              </Button>
            </Group>
          </div>
        ) : (
          <>
            <Title order={2} ta="center" mb="md">
              Установите новый пароль
            </Title>
            
            <form onSubmit={handleSubmit}>
              <PasswordInput
                label="Новый пароль"
                placeholder="Минимум 8 символов"
                required
                value={form.data.password}
                onChange={(e) => form.setData('password', e.target.value)}
                error={form.errors.password}
                disabled={form.processing}
                mb="md"
              />
              
              <PasswordInput
                label="Подтверждение пароля"
                placeholder="Повторите новый пароль"
                required
                value={form.data.password_confirmation}
                onChange={(e) => form.setData('password_confirmation', e.target.value)}
                error={form.errors.password_confirmation}
                disabled={form.processing}
                mb="xl"
              />
              
              {passwordError && (
                <Alert color="red" mb="md">
                  {passwordError}
                </Alert>
              )}
    
              {form.errors && Object.keys(form.errors).length > 0 && (
                <Alert color="red" mb="md">
                  Произошла ошибка при смене пароля
                </Alert>
              )}
              
              <Group justify="space-between">
                <Button 
                  component={Link} 
                  href="/en/users/sign_in" 
                  variant="subtle"
                  disabled={form.processing}
                >
                  Назад
                </Button>
                <Button 
                  type="submit" 
                  loading={form.processing}
                  disabled={!form.data.password || !form.data.password_confirmation}
                >
                  Сменить пароль
                </Button>
              </Group>
            </form>
          </>
        )}
      </Card>
    </Container>
  )
}