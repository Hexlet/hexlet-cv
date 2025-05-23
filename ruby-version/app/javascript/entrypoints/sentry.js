import * as Sentry from '@sentry/browser';
import { BrowserTracing } from '@sentry/tracing';

// https://docs.sentry.io/platforms/javascript/
Sentry.init({
  dsn: 'https://85a2460d54d24846963a314a247a790a@o1090356.ingest.sentry.io/4504576077004800',
  integrations: [new BrowserTracing()],
  tracesSampleRate: 0.001,
  ignoreErrors: [
    'top.GLOBALS', // Random plugins/extensions
    'VK is',
    'VK.Retargeting is ',
    "Can't find variable: VK",
    'pktAnnotationHighlighter',
    'Unexpected keyword',
    'illegal character',
    'Unexpected identifier',
    'Illegal invocation',
    'missing = in const declaration',
  ],
  allowUrls: ['cv.hexlet.io'],
  beforeSend(event, hint) {
    const stack = hint?.originalException?.stack || '';
    const errorInitiator = stack
      .split('\n')
      .map((line) => line.trim())
      .find((line) => line.startsWith('at'));
    const causedByConsole = errorInitiator
      ? errorInitiator.includes('<anonymous>:')
      : false;
    return causedByConsole ? null : event;
  },
});
