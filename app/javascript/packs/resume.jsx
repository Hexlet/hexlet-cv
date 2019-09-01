// @ts-check

import gon from 'gon';
import React from 'react';
import ReactDOM from 'react-dom';
import Form from 'react-jsonschema-form';

const log = (type) => console.log.bind(console, type);

ReactDOM.render((
  <Form schema={gon.resume_schema}
        onChange={log("changed")}
        onSubmit={log("submitted")}
        onError={log("errors")} />
), document.querySelector('[data-mount="resume"]'));
