spotifyAuth = {


function loginSpotify() {

      let popup = window.open(`https://accounts.spotify.com/authorize?client_id=${this.client_id}&response_type=token&redirect_uri=${this.redirect_uri}&scope=${this.scopes}&show_dialog=true`, 'Login with Spotify', 'width=800,height=600')

      window.spotifyCallback = (payload) => {
        // alert(payload)

        popup.close()

        fetch('https://api.spotify.com/v1/me', {
          headers: {
            'Authorization': `Bearer ${payload}`
          }
        }).then(response => {
          return response.json()
        }).then(data => {
          this.me = data
        })
      }
    }

}