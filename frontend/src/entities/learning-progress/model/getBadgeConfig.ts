import type { ReactNode } from "react";
import type { TFunction } from "i18next";
import type { MantineColor } from "@mantine/core";

export const getBadgeConfig = (
  isCompleted: boolean,
  completedLessons: number,
  t: TFunction, // Стандартный тип для функции перевода
): { label: ReactNode; color: MantineColor } | null => {
  
  if (isCompleted)
    return {
      label: t('accountPage.progress.programBadge.completedProgram'),
      color: 'green', // Mantine понимает это как строку-цвет
    };

  if (completedLessons === 0)
    return {
      label: t('accountPage.progress.programBadge.newProgram'),
      color: 'blue',
    };

  return null;
};
