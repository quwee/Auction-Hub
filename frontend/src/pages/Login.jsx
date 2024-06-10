import React, {useState} from 'react';
import cl from "../styles/Login.module.css";
import {Link, useNavigate} from "react-router-dom";
import AuthService from "../API/service/AuthService";
import {useAuthContext} from "../context/context";
import Loader from "../components/ui/loader/Loader";

const Login = () => {

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [emailError, setEmailError] = useState('ERROR')
    const [passwordError, setPasswordError] = useState('ERROR')
    const [isEmailError, setIsEmailError] = useState(false)
    const [isPasswordError, setIsPasswordError] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

    const {setIsAuth, setUser} = useAuthContext()
    const navigate = useNavigate()

    const validate = () => {
        let isError = false

        if (!email) {
            setEmailError('Заполните поле')
            setIsEmailError(true)
            isError = true
        } else {
            setIsEmailError(false)
        }

        if (!password) {
            setPasswordError('Заполните поле')
            setIsPasswordError(true)
            isError = true
        } else {
            setIsPasswordError(false)
        }

        return isError
    }

    const handleSignInClick = async () => {
        let isError = validate()
        if(isError) {
            return
        }

        setIsLoading(true)

        let userLoginRequestDto = {
            'email': email,
            'password': password
        }

        let response = await AuthService.login(userLoginRequestDto)

        if (!response) {
            console.log('error', response)
            setIsLoading(false)
            alert('Сервер не отвечает')
            return
        }

        if (response.status === 200) {
            localStorage.setItem('accessToken', response.data.accessToken)
            localStorage.setItem('refreshToken', response.data.refreshToken)
            localStorage.setItem('user', JSON.stringify(response.data.user))

            setIsAuth(true)
            setUser(response.data.user)

            navigate('/auctions')
        } else if (response.status === 400 || response.status === 404 || response.status === 401) {
            response.data.errors.forEach(error => {
                if (error.field === 'email') {
                    setEmailError(error.message)
                    setIsEmailError(true)
                }
                if (error.field === 'password') {
                    setPasswordError(error.message)
                    setIsPasswordError(true)
                }
            })
        } else {
            console.log('error', response)
            alert('Ошибка сервера')
        }
        setIsLoading(false)
    }

    return (
        <div className={cl.container}>
            <div className={cl.form}>
                <p className={cl.header}>Вход</p>
                <div className={cl.input__container}>
                    <input
                        className={cl.input}
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        type="text"
                        placeholder="Почта"
                        maxLength="200"/>
                </div>
                <p
                    className={cl.error}
                    style={{visibility: isEmailError ? "visible" : "hidden"}}>
                    {emailError}
                </p>
                <div className={cl.input__container}>
                    <input
                        className={cl.input}
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        type="password"
                        placeholder="Пароль"
                        maxLength="100"/>
                </div>
                <p
                    className={cl.error}
                    style={{visibility: isPasswordError ? "visible" : "hidden"}}>
                    {passwordError}
                </p>
                <p className={cl.signup__container}>
                    <Link className={cl.signup} to="/registration">Зарегистрироваться</Link>
                </p>
                <div
                    className={cl.button__container}
                    onClick={handleSignInClick}>
                    <p className={cl.button}>
                        Войти
                    </p>
                </div>
            </div>
            <div style={{display: isLoading ? 'flex' : 'none'}} className={cl.modal__container}>
                <Loader/>
            </div>
        </div>
    );
};

export default Login;