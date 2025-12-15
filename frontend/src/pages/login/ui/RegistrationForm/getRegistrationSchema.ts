import * as yup from "yup"
import { BLOCKED_DOMAINS } from "../../../../utils/emailDomains"

export const getRegistrationSchema = (t) => {
    return yup.object().shape({
        lastName: yup.string()
            .min(4, t("registerPage.errors.minLettersErr", { word: t("registerPage.lastName")}))
            .required(t("registerPage.errors.required")),
        firstName: yup.string()
            .min(4, t("registerPage.errors.minLettersErr", { word: t("registerPage.firstName")}))
            .required(t("registerPage.errors.required")),
        email: yup
            .string()
            .required(t("registerPage.errors.emailError"))
            .email(t("registerPage.errors.emailError"))
            .test(
                'is-not-temporary',
                t("registerPage.errors.emailErrorTemporary"),
                function(value) {
                    if(!value) return true
                    const domain = value.split("@")[1].toLowerCase()
                    if(BLOCKED_DOMAINS.includes(domain)) return false
                    return true
                }
            ),
        password: yup
                .string()
                .min(8, t("registerPage.errors.passwordMinErr"))
                .required(t("registerPage.errors.required"))
                .test(
                    "is-not-personal-info",
                    t("registerPage.errors.passwordNotPersonalInfoErr"),
                    function(value: string) {
                        const { firstName, lastName, email } = this.parent
                        if(!value) return true

                        const lowerPassword = value.toLowerCase()
                        if (firstName && lowerPassword.includes(firstName.toLowerCase())) return false
                        if (firstName && lowerPassword.includes(lastName.toLowerCase())) return false
                        if(email) {
                            const emailPart = email.split('@')[0].toLowerCase()
                            if (email && lowerPassword.includes(emailPart)) {
                                return false
                            }
                        }
                        return true
                    }
                ),
        confirmPassword: yup
            .string()
            .required(t("registerPage.errors.required"))
            .oneOf([yup.ref("password"), null], t("registerPage.errors.confirmPasswordErr"))
        })
}