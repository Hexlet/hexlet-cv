import { Text } from '@mantine/core'
import classes from './AdminNavbar.module.css'
import { Link, usePage } from '@inertiajs/react'
import {
  IconSpeakerphone,
  IconVideo,
  IconBooks,
  IconMicrophone,
  IconStar,
  IconSchool,
  IconUsers,
  IconSettings,
  IconHelp,
} from '@tabler/icons-react'

const iconsMap: any = {
  IconSpeakerphone: IconSpeakerphone,
  IconVideo: IconVideo,
  IconBooks: IconBooks,
  IconMicrophone: IconMicrophone,
  IconStar: IconStar,
  IconSchool: IconSchool,
  IconUsers: IconUsers,
  IconSettings: IconSettings,
  IconHelp: IconHelp,
}

export type Item = {
  label: string
  link: string
  icon: string
}

export type AdminNavbarEntry = {
    category: string
    items: Item[]
}

export const AdminNavbar: React.FC = (): JSX.Element => {
  const { url, props } = usePage<any>()
  const menuData = props.menu as AdminNavbarEntry[]

  return (
    <nav className={classes.navbar}>
        {menuData.map(group => (
          <div className={classes.section} key={group.category}>
            <Text c='dimmed' size='xs' pl='sm'>
              {group.category}
            </Text>
            {group.items.map(item => {
              const isActive = url.includes(item.link)
              const IconComponent = iconsMap[item.icon]
              return (
                <Link
                  key={item.label}
                  className={classes.link}
                  href={item.link}
                  data-active={isActive || undefined}
                >
                  <IconComponent className={classes.linkIcon} stroke={1.5} />
                  <span>{item.label}</span>
                </Link>
              )
            })}
          </div>
        ))}
    </nav>
  )
}
