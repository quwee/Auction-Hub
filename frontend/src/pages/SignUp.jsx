import React, {useState} from 'react'
import cl from "../styles/SignUp.module.css"
import {Link, useNavigate} from "react-router-dom"
import AuthService from "../API/service/AuthService"
import Loader from "../components/ui/loader/Loader";

const SignUp = () => {

    const [email, setEmail] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [password, setPassword] = useState('')
    const [passwordConfirmation, setPasswordConfirmation] = useState('')
    const [emailError, setEmailError] = useState('ERROR')
    const [firstNameError, setFirstNameError] = useState('ERROR')
    const [lastNameError, setLastNameError] = useState('ERROR')
    const [passwordError, setPasswordError] = useState('ERROR')
    const [passwordConfirmationError, setPasswordConfirmationError] = useState('ERROR')
    const [isEmailError, setIsEmailError] = useState(false)
    const [isFirstNameError, setIsFirstNameError] = useState(false)
    const [isLastNameError, setIsLastNameError] = useState(false)
    const [isPasswordError, setIsPasswordError] = useState(false)
    const [isPasswordConfirmationError, setIsPasswordConfirmationError] = useState(false)
    const [isLoading, setIsLoading] = useState(false)

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

        if (!firstName) {
            setFirstNameError('Заполните поле')
            setIsFirstNameError(true)
            isError = true
        } else {
            setIsFirstNameError(false)
        }

        if (!lastName) {
            setLastNameError('Заполните поле')
            setIsLastNameError(true)
            isError = true
        } else {
            setIsLastNameError(false)
        }

        if (!password) {
            setPasswordError('Заполните поле')
            setIsPasswordError(true)
            isError = true
        } else {
            setIsPasswordError(false)
        }

        if (!passwordConfirmation) {
            setPasswordConfirmationError('Заполните поле')
            setIsPasswordConfirmationError(true)
            isError = true
        } else {
            setIsPasswordConfirmationError(false)
        }

        if (password !== passwordConfirmation) {
            setPasswordError('Пароли не совпадают')
            setPasswordConfirmationError('Пароли не совпадают')
            setIsPasswordError(true)
            setIsPasswordConfirmationError(true)
            isError = true
        }

        return isError
    }

    const handleSignUpClick = async () => {
        let isError = validate()
        if (isError) {
            return
        }

        setIsLoading(true)

        let userRegisterRequestDto = {
            'email': email,
            'password': password,
            'firstName': firstName,
            'lastName': lastName,
            'passwordConfirmation': passwordConfirmation
        }

        let response = await AuthService.register(userRegisterRequestDto)

        if (!response) {
            console.log('error', response)
            setIsLoading(false)
            alert('Сервер не отвечает')
            return
        }

        if (response.status === 200) {
            navigate('/login')
        } else if (response.status === 400 || response.status === 409) {
            response.data.errors.forEach(error => {
                if (error.field === "email") {
                    setEmailError(error.message)
                    setIsEmailError(true)
                }
                if (error.field === "firstName") {
                    setFirstNameError(error.message)
                    setIsFirstNameError(true)
                }
                if (error.field === "lastName") {
                    setLastNameError(error.message)
                    setIsLastNameError(true)
                }
                if (error.field === "password") {
                    setPasswordError(error.message)
                    setIsPasswordError(true)
                }
                if (error.field === "passwordConfirmation") {
                    setPasswordConfirmationError(error.message)
                    setIsPasswordConfirmationError(true)
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
                <p className={cl.header}>Регистрация</p>

                {/*   email   */}
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

                {/*   firstName   */}
                <div className={cl.input__container}>
                    <input
                        className={cl.input}
                        value={firstName}
                        onChange={e => setFirstName(e.target.value)}
                        type="text"
                        placeholder="Имя"
                        maxLength="200"/>
                </div>
                <p
                    className={cl.error}
                    style={{visibility: isFirstNameError ? "visible" : "hidden"}}>
                    {firstNameError}
                </p>

                {/*   lastName   */}
                <div className={cl.input__container}>
                    <input
                        className={cl.input}
                        value={lastName}
                        onChange={e => setLastName(e.target.value)}
                        type="text"
                        placeholder="Фамилия"
                        maxLength="200"/>
                </div>
                <p
                    className={cl.error}
                    style={{visibility: isLastNameError ? "visible" : "hidden"}}>
                    {lastNameError}
                </p>

                {/*   password   */}
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

                {/*   passwordConfirmation   */}
                <div className={cl.input__container}>
                    <input
                        className={cl.input}
                        value={passwordConfirmation}
                        onChange={e => setPasswordConfirmation(e.target.value)}
                        type="password"
                        placeholder="Подтвердите пароль"
                        maxLength="100"/>
                </div>
                <p
                    className={cl.error}
                    style={{visibility: isPasswordConfirmationError ? "visible" : "hidden"}}>
                    {passwordConfirmationError}
                </p>

                <p className={cl.signup__container}>
                    <Link className={cl.signup} to="/login">Войти</Link>
                </p>
                <div
                    className={cl.button__container}
                    onClick={handleSignUpClick}>
                    <p className={cl.button}>
                        Зарегистрироваться
                    </p>
                </div>
            </div>
            <div style={{display: isLoading ? 'flex' : 'none'}} className={cl.modal__container}>
                <Loader/>
            </div>
        </div>
    )
}

export default SignUp