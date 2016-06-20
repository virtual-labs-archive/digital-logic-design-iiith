# JCC = javac
# JFLAGS = -g
# sources = $(lab*.java)
# classes = $(sources: .java=.class)
# all: $(classes)
# %.class* : %.java*
# 	$(JAVAC) $<

# clean:
# 	$(RM) *.class

# build-dir=../build
# lib-dir=lib
# build-js-dir=${build-dir}/js

# theme=blue-icon


# all:  help-dir 

# help-dir:
# 	(find ${build-dir} -mindepth 1 -maxdepth 1 ! -name js -print0 | xargs -0n1 rm -rf)
# 	(touch ${build-dir}/theme-${theme}; rsync -avC --exclude ${mathjax}.zip lab/* lib/* help themes/${theme}/* ${build-dir})





# # don't remove the js directory, since copying it again with mathjax takes too long.
# # http://superuser.com/questions/168130/unix-delete-files-and-folders-excluding-pattern
# clean:
# 	(find ${build-dir} -mindepth 1 -maxdepth 1 ! -name js -print0 | xargs -0n1 rm -rf)

build-dir=../build
theme=green-icon

all: ${theme}

green-icon:
	mkdir ${build-dir};rsync -avC --progress lab/* themes/${theme}/* ${build-dir}
clean:
	rm -rf ${build-dir}









