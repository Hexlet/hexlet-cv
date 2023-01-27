import * as Sentry from '@sentry/browser';
import { BrowserTracing } from '@sentry/tracing';

// https://docs.sentry.io/platforms/javascript/
// NOTE: Config via env variable SENTRY_DSN, SENTRY_RELEASE
Sentry.init({
  integrations: [new BrowserTracing()],
  debug: true,
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
  allowUrls: [/https?:\/\/cv.hexlet.io/],
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
