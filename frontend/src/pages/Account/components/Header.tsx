import { Autocomplete, Group } from '@mantine/core'
import classes from './Header.module.css'
import { IconSearch } from '@tabler/icons-react'

export const Header: React.FC = () => {
  return (
    <header className={classes.header}>
      <div className={classes.inner}>
        <Group>Личный кабинет</Group>
        <Group>
          <Autocomplete
            className={classes.search}
            placeholder="Search"
            leftSection={<IconSearch size={16} stroke={1.5} />}
            data={[]}
            visibleFrom="xs"
          />
        </Group>
      </div>
    </header>
  )
}
