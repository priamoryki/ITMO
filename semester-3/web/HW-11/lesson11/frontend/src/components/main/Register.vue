<template>
    <div class="register form-box">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" v-model="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="username">
                        <label for="username">Username</label>
                    </div>
                    <div class="value">
                        <input id="username" name="username" v-model="username"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" v-model="password"/>
                    </div>
                </div>
                <div class="error">{{ error }}</div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    data: function () {
        return {
            login: "",
            username: "",
            password: "",
            error: ""
        }
    },
    name: "Register",
    beforeCreate() {
        this.$root.$on("onRegisterValidationError", (error) => {
            this.error = error;
        });
    },
    beforeMount() {
        this.$root.$on("onRegisterValidationError", error => this.error = error);
    }, methods: {
        onRegister: function () {
            this.$root.$emit("onRegister", this.login, this.username, this.password);
        }
    }
}
</script>

<style scoped>

</style>