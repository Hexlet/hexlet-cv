import React, { useState } from 'react'
import { useForm } from '@inertiajs/react'
import { Modal, TextInput, Button, Alert, Text, Group } from '@mantine/core'

interface ResetPasswordModalProps {
    opened: boolean
    onClose: () => void
}

export default function ResetPasswordModal({ opened, onClose }: ResetPasswordModalProps) {
    const [success, setSuccess] = useState(false)
    const [resetError, setResetError] = useState<string | null>(null)

    const form = useForm({
        email: '',
    })

    const validateEmail = () => {
        if (!/^\S+@\S+$/.test(form.data.email)) return 'Введите корректный email'
        return null
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        setResetError(null)

        const err = validateEmail()
        if (err) {
            setResetError(err)
            return
        }

        form.post('/en/users/password/reset', {
            onSuccess: () => {
                setSuccess(true)
                form.reset()
            },
            onError: (errs) => {
                console.error('Ошибка сброса пароля', errs)
                setResetError('Произошла ошибка. Попробуйте позже.')
            },
        })
    }

    return (
        <Modal
            opened={opened}
            onClose={onClose}
            title="Восстановление пароля"
            centered
            size=""
            radius="lg"
            overlayProps={{
                backgroundOpacity: 0.8,
                blur: 0,
                color: '#000',
            }}
            styles={{
                close: {
                    marginTop: '-20px',
                    color: '#adb5bd'
                },
                title: {
                    fontWeight: 600,
                },
            }}
        >
            {success ? (
                <div>
                    <Alert color="green" mb="md">
                        <Text size="sm">
                            Если аккаунт с таким email существует, мы отправили письмо с инструкциями для сброса пароля.
                            Пожалуйста, проверьте вашу почту.
                        </Text>
                    </Alert>
                    <Group justify="center">
                        <Button onClick={onClose} variant="light"
                        >
                            Закрыть
                        </Button>
                    </Group>
                </div>
            ) : (
                <form onSubmit={handleSubmit}>
                    <Text size="sm"  mb="md">
                        Введите ваш email
                    </Text>

                    <TextInput
                        placeholder="E-mail"
                        required
                        radius="md"
                        value={form.data.email}
                        onChange={(e) => form.setData('email', e.target.value)}
                        error={form.errors.email}
                        disabled={form.processing}
                        mb="md"
                    />

                    {resetError && (
                        <Alert color="red" mb="md">
                            {resetError}
                        </Alert>
                    )}

                    {form.errors && Object.keys(form.errors).length > 0 && (
                        <Alert color="red" mb="md">
                            Произошла ошибка при отправке запроса
                        </Alert>
                    )}

                    <Group justify="space-between" mt="xl">
                        <Button
                            type="submit"
                            radius="md"
                            loading={form.processing}
                        >
                            Отправить ссылку для сброса
                        </Button>
                        <Button variant="outline"
                        radius="md"
                            styles={{
                                root: {
                                    borderColor: '#dee2e6',
                                    color: '#495057',
                                    '&:hover': {
                                        backgroundColor: '#f8f9fa',
                                        borderColor: '#adb5bd',
                                    },
                                },
                            }}
                            onClick={onClose}
                            disabled={form.processing}>
                            Отмена
                        </Button>

                    </Group>
                </form>
            )}
        </Modal>
    )
}