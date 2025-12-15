import logo from "../../../../shared/logo_Min.png"
import { yupResolver } from "mantine-form-yup-resolver";
import { useForm } from "@mantine/form"
import { useTranslation } from "react-i18next";
import { useDisclosure } from "@mantine/hooks";
import { motion } from "motion/react"
import {
    Burger,
    Container,
    Button,
    Group,
    Image,
    Anchor,
    TextInput,
    PasswordInput,
    Menu,
    UnstyledButton,
    List
} from "@mantine/core";
import { getRegistrationSchema } from "./getRegistrationSchema";
import { useState, useMemo } from "react";
import { animate } from "motion";

const Demo = () => {
    const { t, i18n } = useTranslation()

    const registrationSchema = useMemo(() => {
        return getRegistrationSchema(t)
    }, [i18n.language, t])

    const data = [
        { label: t("registerPage.ru")},
        { label: t("registerPage.en")}
    ]
    const form = useForm({
        initialValues: {
            lastName: '',
            firstName: '',
            email: '',
            password: '',
            confirmPassword: '',
        },
        validate: yupResolver(registrationSchema)
    })

    const LanguagePicker = () => {
        const { i18n } = useTranslation()
        const [opened, setOpened] = useState(false)

        const currentLangLabel = i18n.language
        const selectedItem = data.find(item => item.label.toLowerCase() === currentLangLabel)
        const dataN = data.filter(item => item.label !== selectedItem.label)
        const items = dataN.map((item) => (
            <Menu.Item
                className="flex items-center w-full justify-center py-1 px-2 transition hover:bg-[var(--mantine-color-blue-filled-hover)]"
                onClick={(e) => {
                    e.stopPropagation()
                    i18n.changeLanguage(item.label.toLowerCase()
                )
                }}
                key={item.label}
            >
                {item.label}
            </Menu.Item>
        ))

        return (
            <Menu
                onOpen={() => setOpened(true)}
                onClose={() => setOpened(false)}
                radius="md"
                width="target"
                withinPortal
            >
                <Menu.Target>
                    <UnstyledButton data-expanded={opened || undefined} className="w-full">
                        <Group gap="xs" className="bg-zinc-500 py-1.5 px-2 rounded-md transition hover:bg-[var(--mantine-color-blue-filled-hover)]">
                            <span>{selectedItem.label}</span>
                        </Group>
                    </UnstyledButton>
                </Menu.Target>
                <Menu.Dropdown className="transition hover:bg-[var(--mantine-color-blue-filled-hover)]">{items}</Menu.Dropdown>
            </Menu>
        )
    }


    const BurgerMenu = () => {
        const [opened, { toggle }] = useDisclosure()
        return (
            <Menu position="bottom-end">
                <Menu.Target>
                    <Burger
                        className="*:bg-zinc-400 *:before:bg-zinc-400 *:after:bg-zinc-400"
                        opened={opened}
                        onClick={toggle}
                        aria-label="Toggle navigation"
                    />
                </Menu.Target>

                <Menu.Dropdown className="mt-[3px] bg-[#1d1f21]">
                    <motion.div
                        initial={{ opacity: 0, scale: 0 }}
                        animate={{ opacity: 1, scale: 1 }}
                        exit={{ opacity: 0, scale: 0 }}
                        key="box">
                        <Menu.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.reviews")}</Anchor></Menu.Item>
                        <Menu.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.vacancies")}</Anchor></Menu.Item>
                        <Menu.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.employment")}</Anchor></Menu.Item>
                        <Menu.Item className="transition bg-zinc-500 w-full rotate-0 my-[2px]">{t("registerPage.signInBtn")}</Menu.Item>
                        <Menu.Item disabled variant="filled" className="my-[2px] rotate-0 cursor-not-allow bg-[var(--mantine-color-blue-9)] transition text-zinc-100">{t("registerPage.signUpBtn")}</Menu.Item>
                        <LanguagePicker />
                    </motion.div>
                </Menu.Dropdown>
            </Menu>
        )
    }

    return (
        <div className="bg-[#181a1b] flex flex-col min-h-screen">
            <header className="bg-[#1d1f21]">
                <Container className="2xl:p-0 xl:px-6" size="xl">
                    <nav className="h-14 pt-2 pb-2 flex justify-between items-center">
                        <Image w={120} h={30} src={logo} alt="Logo" />
                        <Group className="lg:hidden">
                            <BurgerMenu />
                        </Group>
                        <Group gap="lg" className="justify-between w-full *:flex hidden lg:flex">
                            <List className="xl:w-1/3 w-1/2 justify-around">
                                <List.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.reviews")}</Anchor></List.Item>
                                <List.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.vacancies")}</Anchor></List.Item>
                                <List.Item><Anchor className="text-zinc-400 transition hover:text-zinc-100 hover:no-underline">{t("registerPage.employment")}</Anchor></List.Item>
                            </List>
                            <List className="*:ml-[14px]">
                                <List.Item><Button className="transition bg-zinc-500">{t("registerPage.signInBtn")}</Button></List.Item>
                                <List.Item><Button disabled variant="filled" className="cursor-not-allowed bg-sky-700 transition text-zinc-100">{t("registerPage.signUpBtn")}</Button></List.Item>
                                <List.Item><LanguagePicker /></List.Item>
                            </List>
                        </Group>
                    </nav>
                </Container>
            </header>
            <main className="grow block px-4 items-center min-[570px]:flex min-[570px]:px-0">
                <Container className="py-4 px-6 bg-[#1d1f21] my-6 rounded-xl">
                    <h2 className="text-[26px] text-zinc-400">{t("registerPage.form.title")}</h2>
                    <form className="min-[370px]:min-w-full min-[570px]:min-w-[500px]" onSubmit={form.onSubmit((values) => console.log(values))}>
                        <TextInput
                            className="mt-2 h-[66px]"
                            classNames={{
                                input:"h-[40px] text-lg border-1 rounded-lg text-[1.2rem] bg-[#1d1f21] text-zinc-400",
                                label: "mb-1 text-[16px] text-zinc-400",
                            }}
                            withAsterisk
                            label={t("registerPage.form.lastName")}
                            placeholder={t("registerPage.form.lastName").toLowerCase()}
                            key={form.key('lastname')}
                            {...form.getInputProps('lastName')}
                        />

                        <TextInput
                            className="mt-6 h-[66px]"
                            classNames={{
                                input:"h-[40px] text-lg border-1 rounded-lg text-[1rem] bg-[#1d1f21] text-zinc-400",
                                label: "mb-1 text-[16px] text-zinc-400"
                            }}
                            withAsterisk
                            label={t("registerPage.form.firstName")}
                            placeholder={t("registerPage.form.firstName").toLowerCase()}
                            key={form.key('firstname')}
                            {...form.getInputProps('firstName')}
                        />

                        <TextInput
                            className="mt-6 h-[66px]"
                            classNames={{
                                input:"h-[40px] text-lg border-1 rounded-lg text-[1rem] bg-[#1d1f21] text-zinc-400",
                                label: "mb-1 text-[16px] text-zinc-400",
                            }}    
                            withAsterisk
                            label={t("registerPage.form.email")}
                            placeholder="your@email.com"
                            key={form.key('email')}
                            {...form.getInputProps('email')}
                        />

                        <PasswordInput
                            className="mt-6 h-[66px]"
                            classNames={{
                                input:"h-[40px] text-lg border-1 rounded-lg text-[1rem] py-[6px] bg-[#1d1f21] text-zinc-400",
                                label: "mb-1 text-[16px] text-zinc-400"
                            }}
                            withAsterisk
                            label={t("registerPage.form.password")}
                            placeholder={t("registerPage.form.password").toLowerCase()}
                            key={form.key('password')}
                            {...form.getInputProps('password')}
                        />

                        <PasswordInput
                            className="mt-6 h-[66px]"
                            classNames={{
                                input:"h-[40px] text-lg border-1 rounded-lg text-[1rem] py-[6px] bg-[#1d1f21] text-zinc-400",
                                label: "mb-1 text-[16px] text-zinc-400"
                            }}
                            withAsterisk
                            label={t("registerPage.form.passwordConfirm")}
                            placeholder={t("registerPage.form.passwordConfirm").toLowerCase()}
                            key={form.key('confirmPassword')}
                            {...form.getInputProps('confirmPassword')}

                        />

                        <Button type="submit" className="w-[100%] mt-8 h-12 rounded-lg bg-[#181a1b] text-zinc-400 transition">Регистрация</Button>
                    </form>
                </Container>
            </main>
            <footer className="bg-[#1d1f21] h-full">
                <Container className="p-[24px] flex justify-between lg:justify-around place-content-around grid-cols-3 flex-wrap" size="xl">
                    <Group className="block w-full min-[570px]:w-1/2 md:w-auto">
                        <h2 className="text-[22px] lg:text-[28px]">{t("registerPage.form.hexlet")}</h2>
                        <ul className="*:mt-4 text-[16px] text-zinc-400">
                            <li><a target="_blank" href="">{t("registerPage.form.rating")}</a></li>
                            <li><a target="_blank" href="">{t("registerPage.form.about")}</a></li>
                            <li><a target="_blank" href="">{t("registerPage.form.sourceCode")}</a></li>
                        </ul>
                    </Group>
                    <Group className="block w-full mt-[20px] min-[570px]:w-1/2 md:w-auto md:mt-0">
                        <h2 className="text-[22px] lg:text-[28px]">{t("registerPage.form.help")}</h2>
                        <ul className="*:mt-4 text-[16px] text-zinc-400">
                            <li><a target="_blank" href="https://ru.hexlet.io/blog/categories/success">{t("registerPage.form.successStories")}</a></li>
                            <li><a target="_blank" href="https://github.com/Hexlet/ru-test-assignments"></a>{t("registerPage.form.testAssignments")}</li>
                            <li><a target="_blank" href="https://ru.hexlet.io/courses/employment">{t("registerPage.form.employmentCourse")}</a></li>
                        </ul>
                    </Group>
                    <Group className="block w-full mt-[20px] min-[570px]:w-1/2 md:w-auto md:mt-0">
                        <h2 className="text-[22px] lg:text-[28px]">{t("registerPage.form.additionally")}</h2>
                        <ul className="*:mt-4 text-[16px] text-zinc-400">
                            <li><a target="_blank" href="https://code-basics.com">{t("registerPage.form.codeBasics")}</a></li>
                            <li><a target="_blank" href="https://codebattle.hexlet.io">{t("registerPage.form.codeBattle")}</a></li>
                            <li><a target="_blank" href="https://hexlet.io/pages/recommended-books">{t("registerPage.form.books")}</a></li>
                        </ul>
                    </Group>
                </Container>
            </footer>
        </div>
    )
}

export default Demo