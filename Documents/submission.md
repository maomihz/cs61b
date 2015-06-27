## School's Submission System Explained

__For cs61bl students__

These days I heard a lot of people around asking about problems about the submission process and ssh. Those are all unix stuff, but most people are not used to Unix, so there may be wierd things happen like why I cannot copy files to the lab computer or why I submit a long list of files. In this post, I will try to explain all the concepts to help ease this summer's learning. Feel free to ask me any questions. BTW, I'm a student in Alice's section. 

I do not mind copying my work, and improvements are expected. There may also be some problem with the language. If you use GitHub and are willing to help me write other instructions or improve this, please tell me. 

__Some of the topics explained may *NOT* apply to a Microsoft Windows computer! (See Mirosoft Windows section below)__

### Introduction to lab's computer

Lab's computer is very different from the computers we see in everyday life. There is a central server that is in charge of all the computing work and resource management. All the computers in the lab have actually no computing power and are all connected to the central server. That's why Ubuntu Linux is used, because one of the features of Linux is multiuser. 

Everyone have an account to access the central server. This is to ensure seperation of different user's files without installing different softwares on thousands of different computers. Even if you use different computers everyday, all your files are not deleted. (But keep in mind that your account will be deleted alongside your files after the course ends. )

The central server is open to the Internet, so if you have an account, you can access your lab computer anywhere in the world without entering the lab! (With `ssh` as explained below)

### Submit with my own computer?

`submit` is a program or a script installed on lab computers to submit your work. This is a command line software, so you need to open a terminal on a lab computer to access it. But the software is certainly __NOT__ installed on your own computer! So all you need to do is copy the files to the lab computer and submit. 

So can we use an USB disk to copy the files? Of course you can!(You will find no place to plug in your USB disk though.) But there is a smart way to this: we use `scp`, as explained below, to remotely copy files __over the network__.

### SSH To Your Machine

`ssh` is a program installed on Unix and Mac computers by default. SSH means 'secure shell', and shell is a program that interpret the commands you give to the computer and run certain programs. (Like Siri, who interpret what you say and do certain task). Using `ssh`, you can __remotely__ login to a computer and open a shell program, so you have all the control power for a computer over the network. But what's different from a remote desktop is SSH does not allow graphical windows to open across the network! So you need to type commands to operate the remote computer. ~~(Of course X11 tunnel can do this)~~ Don't be afraid of typing commands! This is the coolest thing a computer science student can do! 

SSH is secure because all the network traffic is encrypted. To open a SSH session, open Terminal in your computer and type `ssh -V` to see if everything works. The output in my computer is `OpenSSH_6.2p2, OSSLShim 0.9.8r 8 Dec 2011`

The basic syntax of `ssh` command is `ssh user@address`. For example, you want to open an ssh session to `192.168.1.1` and login with user account `maomi`, you type `ssh maomi@192.168.1.1` and hit enter. It will prompt you password. As you type, the characters __WON'T__' show up on the screen and when you finished typing, just press enter. 

Now you will get a message like this: 
```
Last login: Sun Jun 21 21:50:25 2015 from 75-147-140-162-sfba.hfc.comcastbusiness.net
[maomi@maomihz.com ~]# 
```

You have successfully logined remotely to your computer!!! For lab's computer, there will be a bunch of messages show up, giving you some warnings like process running for 24 hours will be killed and infomations like disk quota. Then there is a shell __prompt__ telling you to enter commands. Don't mix up your local terminal and remote shell! Once you sshed to a remote server, you should type `exit` logout to return to your own computer. 

### `scp` Your Files

__WARNING__: This section is long and covers lots of UNIX topic. I hope you can read that to improve your knowledge about UNIX. 

This is a confusing part. When you have a lab session, you typically go to the website(`cs61bl.github.io`), download the files, drag into `eclipse`, and start working. And then you will have all the trouble about where is the files you saved, because you have to copy the files and submit them! But do you remember when you first opened eclipse, a dialog prompted you to select the workspace? __Remember all the files inside eclipse is on your workspace folder you set.__ If you don't know where your workspace is, open up eclipse and click `File` -> `Switch Workspace` -> `other`

After you finished working, all the modified files are saved in the eclipse workspace. __I recommend you open the eclipse workspace before submission and drag or copy all the files you need to submit (Shown on the start of every lab section, DO NOT include redundent files for it will prompt you a long list of files to submit!)__ to a seperate folder. (On your Desktop, for example) It will be helpful for your further work!

Now it comes the challenging part: `scp` command. First of all, we will learn about how unix organize files in a filesystem (This applys to Mac and Linux. For windows, see the alternative method but when you are working remotely over SSH, you do need the knowledge.) and various Unix commands you will encounter.

Every user have a __home directory__, which is represented by "~". You can open up a Terminal and try the commands: 

```
$ ls ~
Desktop    Downloads  Pictures  Templates  workspace
Documents  Music      Public    Videos
```

`ls` is a program that lists directory content. Without an argument(parameter), `ls` will list the contents of __working directory__. To have a more detailed listing, use `ls -l`:

```
$ ls -l ~
total 36
drwxr-xr-x 3 maomi cs61bl 4096 Jun 23 18:37 Desktop
drwxr-xr-x 4 maomi cs61bl 4096 Jun 23 18:47 Documents
drwxr-xr-x 2 maomi cs61bl 4096 Jun 23 18:37 Downloads
drwxr-xr-x 2 maomi cs61bl 4096 Jun 22 18:54 Music
drwxr-xr-x 2 maomi cs61bl 4096 Jun 22 18:54 Pictures
drwxr-xr-x 2 maomi cs61bl 4096 Jun 22 18:54 Public
drwxr-xr-x 2 maomi cs61bl 4096 Jun 22 18:54 Templates
drwxr-xr-x 2 maomi cs61bl 4096 Jun 22 18:54 Videos
drwxr-xr-x 6 maomi cs61bl 4096 Jun 23 18:36 workspace
```

To see what is your __working directory__, type `pwd`, which means "Print Working Directory":
```
$ pwd
/Users/maomi/Documents/workspace/cs61b/Documents
```
This is a complete form of your working directory. My home directory is `/Users/maomi/`, so the directory can be abbreviated to `~/Documents/workspace/cs61b/Documents`.

In each folder, there is two special folders, the `./` and `../`. `./` means the __current folder__, and `../` means the current folder's __parent folder__. The paths shown by using `./` and `../` is called __relative path__ and the path start with `/` is called __absolute path__. So if our current working directory is `~/Documents/workspace/cs61b/Documents`, and I want to list the content of the parent folder: 
```
$ pwd
/Users/maomi/Documents/workspace/cs61b/Documents

$ ls ../
Documents
LICENSE
README.md

$ ls ~/Documents/workspace/cs61b/
Documents
LICENSE
README.md

$ ls ../../
FishMimic	PaperDown	cs61b

$ ls ~/Documents/workspace/
FishMimic	PaperDown	cs61b

```

Note that path starting with `~` is also relative path. Paths ending with `/` is usually a folder, although sometimes omitting the trailing `/` is allowed: 
```
$ ls ..
Documents
LICENSE
README.md
```

How do you change your working directory? There is a command called `cd`, which means "Change (working) Directory". The command is pretty staightforward: 
```
$ pwd
/Users/maomi/Documents/workspace/cs61b/Documents

$ cd ..
$ pwd
/Users/maomi/Documents/workspace/cs61b

$ cd ..
$ pwd
/Users/maomi/Documents/workspace

$ cd cs61b/
$ pwd
/Users/maomi/Documents/workspace/cs61b

$ cd ~
$ pwd
/Users/maomi
```

Here I will give you a trick: if you are on a mac and you want to open Finder to explore your current working directory, just type `open .` and a Finder window will appear. Of course you can type `open ~` to open your home directory, and `open /etc/` to open some wierd directorys. 

The last command before scp is the `mv` command. `mv` means "move", and besides moving files and directories, `mv` can rename a file or a directory on the command line. The command is also pretty straightforward, Example: 
```
$ ls
Data Structures and Algorithms in Java, 6th Edition.pdf
Documents
LICENSE
README.md

$ mv LICENSE funstuff
$ ls
Data Structures and Algorithms in Java, 6th Edition.pdf
Documents
README.md
funstuff

$ mv README.md Documents/
$ ls -R
Data Structures and Algorithms in Java, 6th Edition.pdf
Documents
funstuff

./Documents:
README.md	submission.md
```

Finally, let's learn `scp`. `scp` means "Secure Copy", which use the `ssh` protocol to send files, so the syntax is pretty similar to `ssh`: `scp /local/file/or/directory user@address:/remote/directory`. For example, to copy folder `~/Documents/workspace/cs61b/` to remote computers folder `~/Documents`, login with account maomi, we use:
```
$ scp ~/Documents/workspace/cs61b/ maomihz@maomihz.com:~/Documents/
/Users/maomi/Documents/workspace/cs61b: not a regular file

$ scp -r ~/Documents/workspace/cs61b/ maomihz@maomihz.com:~/Documents
COMMIT_EDITMSG                                100%   14     0.0KB/s   00:00
......
```

__REMEMBER__ we must use -r option to copt a whole directory!

Now I assume you have put all your files you want to submit in a folder on Desktop called lab01: `~/Desktop/lab02/`, and inside we have only required files: 
```
$ ls ~/Desktop/lab01/
DateConverter.java
TriangleDrawer.java
CheckDigit.java
AddingMachine.java
AddingMachine.readme
```

The command to copy all the files to lab computer's Desktop is:
```
$ scp -r ~/Desktop/lab01/ cs61bl-**@torus.cs.berkeley.edu:~/Desktop/
......
```

I hope I explained everything needed!

### Submit Your Work

Now that the files you want to submit is already on the lab computer's `~/Desktop/` folder. Either `ssh` and login with your account (see "SSH to your machine" section above") or go to the lab and login to one of the computers. You will see the folder appeared on your desktop magically. The `submit` command work like this: `submit ASSIGN`, where task is the name of the assignment like `lab01, lab02, lab03`. Notice the argument of `submit` is __NOT__ a path!! (The assignment name allowed is pre-defined by the professor) `submit` does not run with no argument. It will submit all the files in the current __Working Directory__, so check your working directory (`pwd`) before submitting!!

```
$ cd ~/Desktop/lab02/

$ pwd
/home/cc/cs61bl/su15/class/cs61bl-xx/Desktop/lab02

$ submit lab02
Please enter the logins of your partner(s), if any.
Enter '.' to stop.
Login: .
You have no partners, correct? [yes/no] yes
Created MY.PARTNERS file.
Looking for files to turn in....
```

__REQUIRED assignment files__ will be turned in __automatically__ without prompt! if you get a prompt like `Turn in ./.bash_logout?  [yes/no]`, the file is certainly not part of assignment. You should always say NO unless you really want to upload the file. 

### Microsoft Windows

If you are on a Microsoft Windows machine, unless you have `cygwin` installed, most of the UNIX commands are inaccessable for you. But you still need UNIX commands when you SSH into your lab computer. For SSH, an alternative to OpenSSH is Putty, which have a graphical user interface for you to fill in the address. Click "Open" and it will prompt you the user to login into. 

If you wish to copy files, an alternative to `scp` is WinSCP, which also have a graphical user interface and features a FTP like experence. Just google the software and install them. The submission process is the same because the commands are executed on a remote computer, not in your windows machine. 

### Congradulations!

Enjoy your course!

-

![Creative Commons](https://i.creativecommons.org/l/by/4.0/88x31.png)
