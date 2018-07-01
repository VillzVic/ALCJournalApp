#create the new directory will contain que October generated apk
  mkdir $ HOME / buildApk / 
  #copy generated apk from build folder to the folder just created
  cp -R app / build / outputs / apk / app-debug.apk $ HOME / android / 
  #go to home and git setup  
  cd $ HOME 
  git config --global user.email "useremail@domain.com" 
  git config --global user.name "Your Name" 
  # Clone the repository in the folder buildApk
  git clone --quiet --branch master = https: // user-name: $6ac399e746fd71c4ceb1f44b8000b9b74eeb1ef1@github.com/VillzVic/ALCJournalApp master> / dev / null 
  #go into directory and copy data we're interested
  cd master cp -Rf $ HOME / android / *. 
  #add, commit and push files
  git add -f. 
  git remote rm origin 
  git remote add origin https: // user-name: $6ac399e746fd71c4ceb1f44b8000b9b74eeb1ef1@github.com/VillzVic/ALCJournalApp.git
   . git add -f 
  git commit -m "Travis build $ TRAVIS_BUILD_NUMBER pushed [skip ci] " 
  git push origin master -fq> / dev / null 
  echo -e" Done \ n "
