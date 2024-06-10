import React, {useContext, useEffect, useState} from 'react';
import NavbarWithMenu from "../components/NavbarWithMenu";
import cl from "../styles/Profile.module.css"
import EditIcon from "../assets/edit.svg"
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../context/context";
import AuthService from "../API/service/AuthService";
import UserService from "../API/service/UserService";
import Loader from "../components/ui/loader/Loader";

const Profile = () => {

    const {setIsAuth, user, setUser} = useAuthContext()

    const navigate = useNavigate()

    const [email, setEmail] = useState(user.email)
    const [firstName, setFirstName] = useState(user.firstName)
    const [lastName, setLastName] = useState(user.lastName)
    const [firstNameError, setFirstNameError] = useState('ERROR')
    const [lastNameError, setLastNameError] = useState('ERROR')
    const [isFirstNameError, setIsFirstNameError] = useState(false)
    const [isLastNameError, setIsLastNameError] = useState(false)

    const [prevFirstName, setPrevFirstName] = useState(user.firstName)
    const [prevLastName, setPrevLastName] = useState(user.lastName)
    const [isEditing, setIsEditing] = useState(false)
    const [isSending, setIsSending] = useState(false)
    const [avatarText, setAvatarText] = useState(() => {
        return firstName.charAt(0) + " " + lastName.charAt(0)
    })

    const validate = () => {
        let isError = false

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

        if (firstName === prevFirstName && lastName === prevLastName) {
            setFirstNameError('Имя осталось прежним')
            setLastNameError('Фамилия осталась прежней')
            setIsFirstNameError(true)
            setIsLastNameError(true)
            isError = true
        }

        return isError
    }

    const handleEditClick = () => {
        setIsEditing(true)
    }

    const handleSaveClick = async () => {
        let isError = validate()
        if (isError) {
            return
        }

        setIsSending(true)

        let changeDetailsRequest = {
            'firstName': firstName,
            'lastName': lastName
        }

        let response = await UserService.changeDetails(changeDetailsRequest)

        if (!response) {
            console.log('error', response)
            alert('Сервер не отвечает')
            setIsSending(false)
            return
        }

        if (response.status === 200) {
            let userCopy = {...user, firstName, lastName}

            setUser(userCopy)
            localStorage.setItem('user', JSON.stringify(userCopy))
            setPrevFirstName(firstName)
            setPrevLastName(lastName)

            setAvatarText(firstName.charAt(0) + " " + lastName.charAt(0))

            setIsEditing(false)
        } else if(response.status === 400) {
            response.data.errors.forEach(error => {
                if (error.field === 'firstName') {
                    setFirstNameError(error.message)
                    setIsFirstNameError(true)
                }
                if (error.field === 'lastName') {
                    setLastNameError(error.message)
                    setIsLastNameError(true)
                }
            })
        } else {
            console.log('error', response)
            alert('Ошибка сервера')
        }
        setIsSending(false)
    }

    const handleCancelClick = () => {
        setIsEditing(false)
        setFirstName(prevFirstName)
        setLastName(prevLastName)
        setIsFirstNameError(false)
        setIsLastNameError(false)
    }

    const handleLogoutClick = async () => {
        setIsSending(true)

        let refreshToken = localStorage.getItem('refreshToken')
        await AuthService.logout(refreshToken)

        localStorage.clear()

        setIsAuth(false)
        setUser(null)
        setIsSending(false)
        navigate('/login')
    }

    return (
        <div>
            <NavbarWithMenu/>
            <div className={cl.container}>
                <div className={cl.content__container}>
                    <div className={cl.image__container}>
                        {avatarText}
                    </div>
                    <div className={cl.info__container}>

                        {/*firstName*/}
                        <div className={cl.info__content__container}>
                            <span className={cl.info__content__label}>Имя:</span>
                            {isEditing ?
                                <input
                                    className={cl.edit__input}
                                    value={firstName}
                                    onChange={e => setFirstName(e.target.value)}
                                    disabled={isSending}
                                    type="text"
                                    maxLength="30"/>
                                :
                                <span className={cl.info__content}>{firstName}</span>
                            }
                        </div>
                        <p
                           className={cl.edit__input__error}
                           style={{visibility: isFirstNameError ? "visible" : "hidden"}}>
                            {firstNameError}
                        </p>

                        {/*lastName*/}
                        <div className={cl.info__content__container}>
                            <span className={cl.info__content__label}>Фамилия:</span>
                            {isEditing ?
                                <input
                                    className={cl.edit__input}
                                    value={lastName}
                                    onChange={e => setLastName(e.target.value)}
                                    disabled={isSending}
                                    type="text"
                                    maxLength="30"/>
                                :
                                <span className={cl.info__content}>{lastName}</span>
                            }
                        </div>
                        <p
                            className={cl.edit__input__error}
                            style={{visibility: isLastNameError ? "visible" : "hidden"}}>
                            {lastNameError}
                        </p>

                        {/*email*/}
                        <div className={cl.info__content__container}>
                            <span className={cl.info__content__label}>Адрес почты:</span>
                            <span className={cl.info__content}>{email}</span>
                        </div>
                        <button
                            style={{visibility: isEditing ? "visible" : "hidden"}}
                            className={cl.edit__button}
                            onClick={handleSaveClick}
                            disabled={isSending}>
                            Сохранить
                        </button>
                        <button
                            style={{visibility: isEditing ? "visible" : "hidden"}}
                            className={cl.cancel__button}
                            onClick={handleCancelClick}
                            disabled={isSending}>
                            Отмена
                        </button>
                        <button
                            className={cl.logout__button}
                            onClick={handleLogoutClick}>
                            Выйти
                        </button>
                    </div>
                    <img
                        hidden={isEditing}
                        className={cl.edit__icon}
                        src={EditIcon}
                        alt=""
                        onClick={() => handleEditClick()}/>
                </div>
            </div>
            <div className={cl.loader__container}>
                <Loader hidden={!isSending} />
            </div>
        </div>
    );
};

export default Profile;