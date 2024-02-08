


    function showReg()
    {
        var upload = document.getElementById('upload');
        upload.style.display = 'none';

        var dead = document.getElementById('dead');
        dead.style.display = 'none';

        var reg = document.getElementById('reg');
        reg.style.display = 'block';

    }

   function showUpload()
        {
            var reg = document.getElementById('reg');
            reg.style.display = 'none';

            var upload = document.getElementById('upload');
            upload.style.display = 'block';

            var dead = document.getElementById('dead');
                    dead.style.display = 'none';
        }


        function showDeadline()
        {
            var reg = document.getElementById('upload');
            reg.style.display = 'none';

            var upload = document.getElementById('dead');
            upload.style.display = 'block';
        }


