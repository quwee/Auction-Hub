import api from "../api";

export default class AuthService {

    static async register(userRegisterRequestDto) {
        const op = 'AuthService:register:'
        console.log(op)
        try {
            return await api.post('auth/register', userRegisterRequestDto)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async confirmRegistration(verificationToken) {
        const op = 'AuthService:confirmRegistration:'
        console.log(op)
        try {
            return await api.post('auth/confirm-registration', verificationToken)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async login(userLoginRequestDto) {
        const op = 'AuthService:login:'
        console.log(op)
        try {
            return await api.post('auth/login', userLoginRequestDto)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }

    static async logout(userLogoutRequestDto) {
        const op = 'AuthService:logout:'
        console.log(op)
        try {
            let response = await api.post('auth/logout', userLogoutRequestDto)
            console.log(`${op} response: `, response)
        } catch (error) {
            console.log(`${op} error: `, error)
        }
    }
}