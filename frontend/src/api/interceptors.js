const setInterceptors = function (instance) {
    instance.interceptors.request.use(
        (config) => {
            const token = localStorage.getItem('Authorization');
            if (token) {
                config.headers['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    )

    instance.interceptors.response.use(
        (response) => {
          const newAccessToken = response.headers['authorization'];
          if (newAccessToken && typeof newAccessToken === 'string') {
            localStorage.setItem('Authorization', newAccessToken);
            instance.defaults.headers.common['Authorization'] = newAccessToken;
          }
          return response;
        },
        async (error) => {
            return Promise.reject(error);
        }
    )
    return instance
};

export {setInterceptors}
