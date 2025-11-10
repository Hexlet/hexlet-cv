import type { FC } from 'react'
import {
  Text,
  Avatar,
  Paper,
  useMantineTheme,
} from '@mantine/core'
import { motion } from 'framer-motion'

interface UserBadgeProps {
  name: string,
  role: string
}

const UserBadge: FC<UserBadgeProps> = ({ name, role }) => {
  const theme = useMantineTheme()

  return (        
    <Paper 
      radius="lg" 
      p="xs"
      withBorder
      shadow="sm"
      bg={theme.colors.dark[6]}
      component={motion.div}
      whileHover={{
        scale: 1.02,
        transition: { duration: 0.2 }
      }}
    >
      <Avatar
        src=""
        size={120}
        radius={120}
        mx="auto"
      />
      <Text ta="center" fz="lg" fw={500} mt="md" c="white">
        {name}
      </Text>
      <Text ta="center" c="dimmed" fz="sm">
        {role}
      </Text>
    </Paper>
  )
}

export default UserBadge