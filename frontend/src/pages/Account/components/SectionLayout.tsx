import classes from './SectionLayout.module.css'

type TProps = {
  children: React.ReactNode
}

export const SectionLayout: React.FC<TProps> = (props) => {
  const { children } = props

  return (
    <main className={classes.section}>
      {children}
    </main>
  )
}
