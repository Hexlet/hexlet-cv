import * as React from 'react'
import classes from './Navbar.module.css'
import { Burger, Drawer, Group, Stack } from '@mantine/core'
import { useDisclosure } from '@mantine/hooks'
import { Link, usePage } from '@inertiajs/react'
import { normalizePathname } from '../lib/normalizePathname'

export interface MenuItem {
  icon?: React.ElementType
  label: string
  link?: string
}

type TProps = {
  data: MenuItem[]
}

export const Navbar: React.FC<TProps> = (props) => {
  const { data } = props

  const { url } = usePage()

  const ativeMenu = data?.find(({ link }) => normalizePathname(link) === normalizePathname(url))

  const [active] = React.useState(ativeMenu?.label || data?.[0]?.label || '')
  const [opened, { toggle }] = useDisclosure(false)

  const links
    = data?.map(item => (
      <Link
        className={classes.link}
        data-active={item.label === active || undefined}
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
}
