import { handlers as homeHadnlers } from '@mocks/home'
import { adminHandlers } from '@mocks/admin'

export const handlers = [...homeHadnlers, ...adminHandlers]
