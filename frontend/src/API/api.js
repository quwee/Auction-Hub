import axios from "axios"

export const API_URL = 'http://localhost:8080/api/v1'

const api = axios.create({
    withCredentials: true,
    baseURL: API_URL
})


api.interceptors.request.use((config) => {
    config.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`
    return config
})

api.interceptors.response.use(
    response => {
        console.log('Success:', response)
        return response
    },
    async (error) => {
        console.log('Not success:', error)

        const originalRequest = error.config

        if (!error.response) {
            return null
        }

        if (error.response.status === 401) {
            console.log('Response status: 401')

            if (error.config && !error.config._isRetry) {
                console.log('Try to get new tokens')

                originalRequest._isRetry = true
                try {
                    const response = await axios.post(
                        `${API_URL}/auth/refresh`,
                        {'refreshToken': localStorage.getItem('refreshToken')},
                        {withCredentials: true})

                    console.log('New tokens received successfully')

                    let user = JSON.stringify(response.data.user)

                    localStorage.setItem('accessToken', response.data.accessToken)
                    localStorage.setItem('refreshToken', response.data.refreshToken)
                    localStorage.setItem('user', user)

                    console.log('Call retry')

                    return api.request(originalRequest)
                } catch (e) {
                    console.log('Fail to receive new tokens:', e)
                    localStorage.clear()
                }
            } else {
                console.log('Retried request failed')
                localStorage.clear()
            }
        }
        return error.response
    })

export default api