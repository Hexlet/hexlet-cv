import * as React from 'react'
import classes from './Navbar.module.css'
import { Burger, Drawer, Group, Stack } from '@mantine/core'
import { useDisclosure } from '@mantine/hooks'
import { Link, usePage } from '@inertiajs/react'
import { normalizePathname } from '@shared/lib/normalizePathname'

export const Navbar: React.FC = React.memo(() => {
  const { props: pageProps, url } = usePage()
  const { menu } = pageProps

  const ativeMenu = menu?.find(({ link }) => normalizePathname(link) === normalizePathname(url)) || menu[0]

  const [opened, { toggle }] = useDisclosure(false)

  const links
    = menu?.map(item => (
      <Link
        className={classes.link}
        data-active={item.label === ativeMenu?.label || undefined}
        href={item.link}
        key={item.label}
      >
        {item.icon && <item.icon className={classes.linkIcon} stroke={1.5} />}
        <span>{item.label}</span>
      </Link>
    )) || []

  return (
    <>
      <nav className={classes.navbar}>
        <Group visibleFrom="xs">
          <div className={classes.navbarMain}>{links}</div>
        </Group>
        <Burger opened={opened} onClick={toggle} hiddenFrom="xs" size="sm" />
      </nav>
      <Drawer opened={opened} onClose={toggle} title="Меню" padding="md" size="xs" hiddenFrom="sm">
        <Stack gap="md">{links}</Stack>
      </Drawer>
    </>
  )
})
