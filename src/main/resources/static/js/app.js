document.addEventListener('DOMContentLoaded', function() {
    const app = document.getElementById('app');
    const pageJson = app.getAttribute('data-page');
    const pageData = JSON.parse(pageJson);

    console.log('🎯 Inertia Component:', pageData.component);
    console.log('📦 Props:', pageData.props);

    // Минимальный рендеринг
    renderComponent(pageData);
});

function renderComponent(page) {
    const app = document.getElementById('app');

    // Простой роутинг по компонентам
    const components = {
        'Auth/Login': renderLogin,
        'Auth/Register': renderRegister,
        'Auth/ForgotPassword': renderForgotPassword,
        'Auth/ResetPassword': renderResetPassword
    };

    const renderFunction = components[page.component] || renderDefault;
    renderFunction(app, page);
}

function renderLogin(container, page) {
    container.innerHTML = `
        <h1>Login</h1>
        <form action="/auth/login" method="POST">
            <input type="hidden" name="_csrf" value="${page.props.csrf}">
            <div>
                <label>Email:</label>
                <input type="email" name="username" required>
                ${page.props.errors.email ? `<span style="color:red">${page.props.errors.email}</span>` : ''}
            </div>
            <div>
                <label>Password:</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <a href="/auth/register">Register</a> |
        <a href="/auth/password/forgot">Forgot Password?</a>
    `;
}

function renderRegister(container, page) {
    container.innerHTML = `
        <h1>Register</h1>
        <form action="/auth/register" method="POST">
            <input type="hidden" name="_csrf" value="${page.props.csrf}">
            <div>
                <label>Email:</label>
                <input type="email" name="email" required>
                ${page.props.errors.email ? `<span style="color:red">${page.props.errors.email}</span>` : ''}
            </div>
            <div>
                <label>Password:</label>
                <input type="password" name="password" required>
                ${page.props.errors.password ? `<span style="color:red">${page.props.errors.password}</span>` : ''}
            </div>
            <button type="submit">Register</button>
        </form>
        <a href="/auth/login">Login</a>
    `;
}

function renderForgotPassword(container, page) {
    container.innerHTML = `
        <h1>Forgot Password</h1>
        <form action="/auth/password/forgot" method="POST">
            <input type="hidden" name="_csrf" value="${page.props.csrf}">
            <div>
                <label>Email:</label>
                <input type="email" name="email" required>
                ${page.props.errors.email ? `<span style="color:red">${page.props.errors.email}</span>` : ''}
            </div>
            <button type="submit">Send Reset Link</button>
        </form>
        <a href="/auth/login">Back to Login</a>
    `;
}

function renderResetPassword(container, page) {
    container.innerHTML = `
        <h1>Reset Password</h1>
        ${!page.props.isValid ? `<p style="color:red">Invalid or expired reset link</p>` : ''}
        <form action="/auth/password/reset" method="POST">
            <input type="hidden" name="_csrf" value="${page.props.csrf}">
            <input type="hidden" name="token" value="${page.props.token}">
            <div>
                <label>New Password:</label>
                <input type="password" name="password" required>
                ${page.props.errors.password ? `<span style="color:red">${page.props.errors.password}</span>` : ''}
            </div>
            <button type="submit">Reset Password</button>
        </form>
    `;
}

function renderDefault(container, page) {
    container.innerHTML = `<h1>${page.component}</h1><pre>${JSON.stringify(page.props, null, 2)}</pre>`;
}